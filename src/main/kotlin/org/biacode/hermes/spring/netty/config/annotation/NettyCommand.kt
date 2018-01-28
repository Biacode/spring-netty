package org.biacode.hermes.spring.netty.config.annotation

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 3:46 PM
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NettyCommand(val value: String)