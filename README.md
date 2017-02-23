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
|  Delete Schema          |  Filter Datasets by licensing        |
|  Update Schema          |  Filter Datasets by type             |
|  Add Schema Field       |  Filter Datasets by topic            |
|  Remove Schema Field    |  Request Dataset for topic           |
|  Update Schema Field    |  Download Dataset File               |
|  Upload Data File       |  Subscribe to Dataset Stream         |
|  Post Stream Record     |  Unsubscribe from Dataset Stream     |
|  Set Dataset License    |  Comment about Topic                 |
|  Comment about Dataset  |  Comment about Dataset               |
|  Define License
|  Add Dataset Owner
|  Remove Dataset Owner

## Entities Model

![Entities Model Diagram](http://g.gravizo.com/g?
@startuml;
skinparam classAttributeFontSize 8;
skinparam classArrowFontSize 9;
skinparam classFontSize 10;
skinparam nodesep 90;
skinparam ranksep 40;
hide stereotype;
skinparam class {;
	BackgroundColor White;
	BackgroundColor<<Data>> LightYellow;
	BackgroundColor<<User>> LightBlue;
	BackgroundColor<<Rights>> LightGreen;
	BackgroundColor<<Metadata>> LightPink;
};
class Dataset <<Data>> {;
	String title, description;
	DateTime dateTime;
	int flags;
	boolean isBlocked;
};
class DataFile <<Data>> extends Dataset {;
	int downloads;
};
class DataStream <<Data>> extends Dataset {;
	int subscribers;
};
Dataset "defines many" -right- "definedBy 1" Schema;
class Schema <<Data>> {;
	String title, description;
};
Schema "partOf 1" -- "contains many" Field;
class Record <<Data>> {;
	String data;
	DateTime timestamp;
};
Record -- Dataset;
class Field <<Data>> {;
	String title, description;
};
Field -- "type 1" FieldType;
enum FieldType <<Data>> {;
	String, Date, Time, ;
	DateTime, Integer,;
	Float, E-Mail,;
	Address, LatLong,...;
};
class Tag <<Metadata>> {;
	String text;
};
Tag "taggedWith many" -- "tags many" Dataset;
class Topic <<Metadata>> {;
    String text;
};
Topic "about many" -- "includes many" Dataset;
Topic "containedBy many" -- "contains many" Tag;
class License <<Rights>> {;
	String text;
};
class OpenLicense <<Rights>> extends License {;
};
class ClosedLicense <<Rights>> extends License {;
	Decimal price;
};
License "governedBy 1" -- "governs many" Dataset;
class Comment <<Metadata>> {;
	String text;
};
Comment "authors many" -- "creator 1" User;
class DatasetComment <<Metadata>> extends Comment {;
};
DatasetComment "subjectOf many" -- "1 about" Dataset;
class TopicComment <<Metadata>> extends Comment {;
	boolean isDatasetRequest;
};
TopicComment "subjectOf many" -- "1 about" Topic;
class User <<User>> {;
	String username, email;
};
class DataUser <<User>> extends User {;
};
DataUser "downloadedBy many" -- "downloaded many" DataFile;
DataUser "subscribedBy many" -- "subscribedTo many" DataStream;
class DataOwner <<User>> extends User {;
	boolean isVerified;
};
Dataset "owns many" -left- "owner many" DataOwner;
class Admin <<User>> extends User {;
};
@enduml
)