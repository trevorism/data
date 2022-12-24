package com.trevorism.gcloud

/**
 * @author tbrooks
 */
class Arbitrary {
    String id
    String name
    Date date
    int number
    double decimal

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Arbitrary arbitrary = (Arbitrary) o

        if (Double.compare(arbitrary.decimal, decimal) != 0) return false
        if (number != arbitrary.number) return false
        if (date != arbitrary.date) return false
        if (id != arbitrary.id) return false
        if (name != arbitrary.name) return false

        return true
    }

    int hashCode() {
        int result
        long temp
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (date != null ? date.hashCode() : 0)
        result = 31 * result + number
        temp = decimal != +0.0d ? Double.doubleToLongBits(decimal) : 0L
        result = 31 * result + (int) (temp ^ (temp >>> 32))
        return result
    }
}
