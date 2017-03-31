# MyData

Class project for the Enterprise Software Architecture 2016-17 Course, Polytechnic School, University of Lleida

[![Build Status](https://travis-ci.org/UdL-EPS-SoftArch/mydata-api.svg?branch=master)](https://travis-ci.org/UdL-EPS-SoftArch/mydata-api/branches) 
<a href="https://zenhub.com"><img src="https://cdn.rawgit.com/ZenHubIO/support/master/zenhub-badge.svg"></a>

## Vision

**For** data owners, **who** are interested in sharing or selling their data, and \
**for** data users, **who** are interested in reusing data and generating new value from it.

The project **MyData** is a data marketplace \
**that** allows users share and describe datasets (files or streams), define their licensing
terms, search datasets considering their popularity, download or subscribe to a dataset, 
request datasets about a given topic, discuss about a particular dataset or topic, 
flag or block an offending or illegal dataset,...

**Unlike** other data portals and marketplaces, this project considers both data files and 
streams, allows open and closed licensing terms and provides a rich set of community building 
mechanisms arround the data.


## Features per Stakeholder

|       Data Owner        |       Data User                      |  Admin
| ------------------------|--------------------------------------|-------------------------
|  Register Dataset       |  List Datasets order by Title        |  Define Topic
|  Delete Dataset         |  List Datasets order by Popularity   |  Associate Tag to Topic
|  Update Dataset         |  Search Dataset by description words |  List Datasets by Flags
|  Define Schema          |  Search Dataset by tag               |  Block Dataset
|  Delete Schema          |  Filter Datasets by licensing        |  Verify Owner
|  Update Schema          |  Filter Datasets by type             |  Delete Tag
|  Add Schema Field       |  Filter Datasets by topic            |  Edit Tag Name
|  Remove Schema Field    |  Request Dataset for topic           |
|  Update Schema Field    |  Download Dataset File               |
|  Upload Data File       |  Subscribe to Dataset Stream         |
|  Post Stream Record     |  Unsubscribe from Dataset Stream     |
|  Set Dataset License    |  Comment about Topic                 |
|  Comment about Dataset  |  Comment about Dataset               |
|  Define License         |  Flag Dataset
|  Add Dataset Owner      |  Unflag Dataset
|  Remove Dataset Owner   |  Create Tag
|                         |  Search Tab by name starting with
|                         |  Tag Dataset
|                         |  Untag Dataset

## Entities Model

![Entities Model Diagram](http://www.plantuml.com/plantuml/svg/JSex3e0W303Gg-W1IdSt1pS74-81452Qy0ksKM-le-DBNa_PHd5UMkm2Spq7n6OkTH07sUJqpPRCFhEo9U7gPfWM3RhiF3ORC3tEi05J2NUwuNl43wJYVCG_L9KKNm00)
