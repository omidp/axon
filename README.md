Axon
====

YET ANOTHER JSON SERIALIZER

Contributors
===

Saber Golanbari
[Omid Pourhadi](http://omidbiz.com)


How to build
===

```
   mvn clean install
```

How to Use
===

```
   AxonBuilder ab = new AxonBuilder();
   ab.useWithPrettyWriter();
   //use filters
   ab.addFilter(new SelectedFilter(fields));
   Axon axon = ab.create();
   //
   axon.toJson(bean);
   //OR
   User u = new User();
   axon.toObject(json, User.class, null|u);
```

for more information please see the test case

