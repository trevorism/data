# data

![Jenkins](https://img.shields.io/jenkins/build/http/trevorism-build.eastus.cloudapp.azure.com/data)
![Jenkins Coverage](https://img.shields.io/jenkins/coverage/jacoco/http/trevorism-build.eastus.cloudapp.azure.com/data)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/data)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/data)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/data)

Creates a high-level interface for data operations for Trevorism Data. The full list of operations can be found [here](https://data.trevorism.com/describe).

Introduces the concept of a data locator of the form <repository>:<path_to_data> which allows all operations to find a dataset.

Operations are classified into two types, Single Datastore and Multi Datastore. A Query interface chains all single datastore operations into a single interface.

Here's an example:

HTTP POST: https://data.trevorism.com/query
```json
{
  "lookup": "datastore:button",
  "fields": [
    "description","name","parameters","topicname"
  ],
  "where": {
    "simpleFilters": [
      {
        "field": "name",
        "operator": "<",
        "value": "Test"
      }
    ]
  },
  "order": {
    "sorts": [
      {
        "field": "topicname",
        "descending": true
      }
    ]
  },
  "limit": {
    "page": 0,
    "pageSize": 0,
    "limit": 3
  }
}

```


Current version: 0.1.0

Deployed to [Trevorism Data](https://data.trevorism.com)