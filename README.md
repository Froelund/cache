Cache
=====

Simple caching for JavaEE

#Usage

```xml
    <!-- Warning: Project isn't quite on Central yet - but hopefully it will be soon. -->
    <dependency>
        <groupId>net.froelund</groupId>
        <artifactId>cache</artifactId>
        <version>0.0.1</version>
        <scope>compile</scope>
    </dependency>
```
Inject cache as follows:
```java
    @Inject
    Cache cache;
```