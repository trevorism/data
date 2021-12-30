package com.trevorism.gcloud.webapi.model.combining

class Join {
    String id
    long sourceDatasetId
    long destinationDatasetId
    List<JoinColumns> joinFields
}
