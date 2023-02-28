package com.sum.tea.network.error

enum class ERROR(val code: Int, val errMsg: String) {

    /**
     * 对应HTTP的状态码
     */
    /**
     * 当前请求需要用户验证
     */
    UNAUTHORIZED(401, "当前请求需要用户验证"),

    /**
     * 资源不可用。服务器理解客户的请求，但拒绝处理它
     */
    FORBIDDEN(403, "资源不可用"),

    /**
     * 无法找到指定位置的资源
     */
    NOT_FOUND(404, "无法找到指定位置的资源"),

    /**
     * 在服务器许可的等待时间内，客户一直没有发出任何请求
     */
    REQUEST_TIMEOUT(408, "请求超时"),

    /**
     * 服务器遇到了意料不到的情况，不能完成客户的请求
     */
    INTERNAL_SERVER_ERROR(500, "服务器错误"),

    /**
     * 服务器作为网关或者代理时，为了完成请求访问下一个服务器，但该服务器返回了非法的应答
     */
    BAD_GATEWAY(502, "非法应答"),

    /**
     * 服务器由于维护或者负载过重未能应答
     */
    SERVICE_UNAVAILABLE(503, "服务器未能应答"),

    /**
     * 由作为代理或网关的服务器使用，表示不能及时地从远程服务器获得应答
     */
    GATEWAY_TIMEOUT(504, "服务器未能应答"),

    /**
     * 未知错误
     */
    UNKNOWN(1000, "未知错误"),

    /**
     * 解析错误
     */
    PARSE_ERROR(1001, "解析错误"),

    /**
     * 网络错误
     */
    NETWORD_ERROR(1002, "网络异常，请尝试刷新"),

    /**
     * 协议出错
     */
    HTTP_ERROR(1003, "404 Not Found"),

    /**
     * 证书出错
     */
    SSL_ERROR(1004, "证书出错"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(1006, "连接超时"),

    /**
     * 未知Host
     */
    UNKNOW_HOST(1007, "未知Host");
}
