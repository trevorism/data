# data
![Build](https://github.com/trevorism/data/actions/workflows/deploy.yml/badge.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/trevorism/data)
![GitHub language count](https://img.shields.io/github/languages/count/trevorism/data)
![GitHub top language](https://img.shields.io/github/languages/top/trevorism/data)

Current version: 0.5.1

Deployed to [Trevorism Data](https://data.trevorism.com)

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
