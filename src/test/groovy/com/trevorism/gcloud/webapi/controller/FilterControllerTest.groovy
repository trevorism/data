package com.trevorism.gcloud.webapi.controller

import com.trevorism.data.Repository
import com.trevorism.gcloud.webapi.model.Filter
import org.junit.Test

class FilterControllerTest {

    @Test
    void testRead() {
        FilterController filterController = new FilterController()
        filterController.filterRepository = [get: { id -> new Filter(id: id, name: "testFilter")}] as Repository
        def readFilter = filterController.read(12345)
        assert readFilter.id == "12345"
        assert readFilter.name == "testFilter"

    }

    @Test
    void testReadAll() {
        FilterController filterController = new FilterController()
        filterController.filterRepository = [list: {[new Filter(id: 12345, name: "testFilter")]}] as Repository
        def listFilter = filterController.readAll()
        assert listFilter
        assert listFilter[0]
    }

    @Test
    void testCreate() {
        FilterController filterController = new FilterController()
        filterController.filterRepository = [create: {new Filter(id: 12345, name: "testFilter")}] as Repository

        Filter filter = new Filter(name: "first", field: "val", operator: "=", value: 2)
        assert filterController.create(filter)
    }

    @Test
    void testUpdate() {
        FilterController filterController = new FilterController()
        filterController.filterRepository = [update: {id, filter -> new Filter(id: 12345, name: "testFilter")}] as Repository

        assert filterController.update(6271336783544320, new Filter(name: "first", field: "val", operator: "=", value: "3"))
    }

    @Test
    void testDelete() {
        FilterController filterController = new FilterController()
        filterController.filterRepository = [delete: { id -> new Filter(id: id, name: "testFilter")}] as Repository
        assert filterController.delete(5164859054358528)
    }
}
