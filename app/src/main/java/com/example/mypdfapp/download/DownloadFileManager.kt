package com.example.mypdfapp.download

interface DownloadFileManager {

    suspend fun download(fileName: String, fileUri: String, headers: Map<String, String> = emptyMap()) : String

}