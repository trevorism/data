package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.transferring.Transfer

interface TransferService {

    def transfer(Long datasetId, Transfer request)
}