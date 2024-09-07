package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.controller.ObjectController
import com.trevorism.https.SecureHttpClient
import org.junit.jupiter.api.Test

class ObjectControllerTest {

    @Test
    void testRead() {
        ObjectController oc = new ObjectController([get: { url -> return "{\"id\":\"12341\",\"name\":\"testName\"}"}] as SecureHttpClient)
        assert oc.read("testpushbutton", "6318495402819584", Optional.of("memory"))
    }

    @Test
    void testReadAll() {
        ObjectController oc = new ObjectController([get: { url -> return "[{\"id\":\"12341\",\"name\":\"testName\"}]"}] as SecureHttpClient)
        assert oc.readAll("testpushbutton", Optional.of("bigquery"))
    }

    @Test
    void testCreate() {
        ObjectController oc = new ObjectController([post: { url, obj -> return "{\"id\":\"12341\",\"name\":\"testName\"}"}] as SecureHttpClient)
        assert oc.create("testpushbutton",[id:12341,name:"testName"], Optional.empty())
    }

    @Test
    void testUpdate() {
        ObjectController oc = new ObjectController([put: { url, obj -> return "{\"id\":\"12341\",\"name\":\"testName\"}"}] as SecureHttpClient)
        assert oc.update("testpushbutton",12341,[id:12341,name:"testName"], Optional.empty())
    }

    @Test
    void testDelete() {
        ObjectController oc = new ObjectController([delete: { url -> return "{\"id\":\"12341\",\"name\":\"testName\"}"}] as SecureHttpClient)
        assert oc.delete("testpushbutton", 6318495402819584, Optional.empty())
    }
}
