package org.biacode.hermes.spring.netty

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 3:20 PM
 */
@ConfigurationProperties("spring.netty")
class NettyProperties {

    /**
     * The host configuration.
     */
    var host: Host? = Host()

    /**
     * The SSL configuration (in particular enabling and disabling).
     */
    var ssl: Ssl? = Ssl()

    /**
     * Netty boss event loop group configuration.
     */
    var boss: Boss? = Boss()

    /**
     * Netty worker event loop group configuration.
     */
    var worker: Worker? = Worker()

    class Host {
        /**
         * Host address. E.g localhost or 127.0.0.1.
         * Default 0.0.0.0
         */
        var address = "0.0.0.0"
        /**
         * Bind port.
         * Default 8081
         */
        var port = 8081
    }

    class Ssl {
        /**
         * Enable or disable SSL.
         * Default false
         */
        var isEnabled = false
    }

    class Boss {
        /**
         * Boss thread settings.
         */
        var thread: Thread? = null

        class Thread {
            /**
             * Boss thread size.
             * Default 1
             */
            private val size = 1
        }
    }

    class Worker {
        /**
         * Worker thread settings.
         */
        var thread: Thread? = null

        class Thread {
            /**
             * Worker thread size.
             * Default 4
             */
            private val size = 4
        }
    }

}
