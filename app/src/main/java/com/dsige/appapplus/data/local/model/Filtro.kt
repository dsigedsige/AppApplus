package com.dsige.appapplus.data.local.model

open class Filtro {

    var usuarioId: Int = 0
    var pageIndex: Int = 0
    var pageSize: Int = 0
    var search: String = ""
    var login: String = ""
    var pass: String = ""
    var imei: String = ""
    var version: String = ""

    constructor()

    constructor(login: String) {
        this.login = login
    }

    constructor(login: String, pass: String, imei: String, version: String) {
        this.login = login
        this.pass = pass
        this.imei = imei
        this.version = version
    }

    constructor(usuarioId: Int, search: String, pageIndex: Int, pageSize: Int) {
        this.usuarioId = usuarioId
        this.search = search
        this.pageIndex = pageIndex
        this.pageSize = pageSize
    }

    constructor(pageSize: Int) {
        this.pageSize = pageSize
    }
}