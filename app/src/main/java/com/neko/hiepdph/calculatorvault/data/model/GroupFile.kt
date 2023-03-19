package com.neko.hiepdph.calculatorvault.data.model

data class GroupFile(
    var name: String,
    var type: String,
    var path: String,
    var dataPathList: MutableList<String>,
    var folderPath: String
) {
    val itemCount:Int
        get() = dataPathList.size
}
