gDao
====
This is a generic dao for spring-hibernate combination. It comes with spring and hibernate dependencies so you don't have to add those dependencies yourself. This also provides the perfect compatibility between versions.

Every other generic dao implementations in the market are based on creating sql queries. gDao just uses hibernate criterias. With this approach, sql injections are prevented. I will be committed to be going on like this, just using hibernate criterias. More secure, more hibernate compitability.

Best feauture of this generic dao is the "SimpleDao" annotation. If you are content with the generic functions of gDao you don't have to create the dao class of the model. For more explanation and usages, see wiki.

https://github.com/mertmr/gDao/wiki
