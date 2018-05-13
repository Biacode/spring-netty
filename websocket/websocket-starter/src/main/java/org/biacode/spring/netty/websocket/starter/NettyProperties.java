package org.biacode.spring.netty.websocket.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 8:03 PM
 */
@ConfigurationProperties("spring.netty")
public class NettyProperties {

    /**
     * The host configuration.
     */
    private Host host = new Host();

    /**
     * Netty boss event loop group configuration.
     */
    private Boss boss = new Boss();

    /**
     * Netty worker event loop group configuration.
     */
    private Worker worker = new Worker();

    /**
     * Websocket configuration.
     */
    private Websocket websocket = new Websocket();

    //region Properties getters and setters
    public Host getHost() {
        return host;
    }

    public void setHost(final Host host) {
        this.host = host;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(final Boss boss) {
        this.boss = boss;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(final Worker worker) {
        this.worker = worker;
    }

    public Websocket getWebsocket() {
        return websocket;
    }

    public void setWebsocket(final Websocket websocket) {
        this.websocket = websocket;
    }
    //endregion

    //region Inner classes
    private static class Host {
        /**
         * Host address. E.g localhost or 127.0.0.1.
         * Default 0.0.0.0
         */
        private String address = "0.0.0.0";
        /**
         * Bind port.
         * Default 8081
         */
        private int port = 8081;

        public String getAddress() {
            return address;
        }

        public void setAddress(final String address) {
            this.address = address;
        }

        public int getPort() {
            return port;
        }

        public void setPort(final int port) {
            this.port = port;
        }
    }

    private static class Boss {
        /**
         * Boss thread settings.
         */
        private Thread thread = new Thread();

        public Thread getThread() {
            return thread;
        }

        public void setThread(final Thread thread) {
            this.thread = thread;
        }

        static class Thread {
            /**
             * Boss thread size.
             * Default 1
             */
            private int size = 1;

            public int getSize() {
                return size;
            }

            public void setSize(final int size) {
                this.size = size;
            }
        }
    }

    private static class Worker {
        /**
         * Worker thread settings.
         */
        private Worker.Thread thread = new Worker.Thread();

        public Worker.Thread getThread() {
            return thread;
        }

        public void setThread(final Worker.Thread thread) {
            this.thread = thread;
        }

        static class Thread {
            /**
             * Worker thread size.
             * Default 4
             */
            private int size = 4;

            public int getSize() {
                return size;
            }

            public void setSize(final int size) {
                this.size = size;
            }
        }
    }

    private static class Websocket {
        /**
         * Websocket path.
         * Default "/"
         */
        private String path = "/";

        public String getPath() {
            return path;
        }

        public void setPath(final String path) {
            this.path = path;
        }
    }
    //endregion

}