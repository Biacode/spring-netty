package org.biacode.hermes.spring.netty.config

import org.biacode.hermes.spring.netty.config.annotation.NettyController
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 9:33 PM
 *
 * This BPP will fix bean definition class name nullability issue for classes
 * which are annotated with NettyController.
 */
@Configuration
class NettyControllerAnnotationBeanDefinitionClassNameAppenderBeanPostProcessor : BeanPostProcessor {

    //region Dependencies
    @Autowired
    private lateinit var configurableListableBeanFactory: ConfigurableListableBeanFactory
    //endregion

    //region Public methods
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean::class.java.isAnnotationPresent(NettyController::class.java)) {
            val beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName)
            if (beanDefinition.beanClassName == null) {
                logger.debug("Fixing bean definition - {} in post process before initialization", beanDefinition)
                beanDefinition.beanClassName = bean.javaClass.canonicalName
            }
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(NettyControllerAnnotationBeanDefinitionClassNameAppenderBeanPostProcessor::class.java)
    }
    //endregion
}