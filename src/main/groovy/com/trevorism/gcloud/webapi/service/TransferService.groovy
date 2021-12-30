package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.transferring.Transfer

interface TransferService {

    def transfer(Transfer request);
}