### Brief introduction



Mybatis universal mapper



### Quick use

+At present, it is not published to the central warehouse. You can clone the project, compile it into jar package with maven, and introduce it locally with Maven.

```xml

<dependency>
  <groupId>%groupId%</groupId>
    <artifactId>%artifactId%</artifactId>
    <version>%version%</version>
    <scope>system</scope>
  <systemPath>${project.basedir}\src\main\libs\%jarName%.jar</systemPath>
</dependency>

```

**If you want to use the general service, you need to add @ componentscan ("CN. Org. Rookie. Tools") to the spring boot class**



+ Create entity

```java

@Table("demo")

public class Demo {



@Primary

private String id;



private String name;



private String birthday;



private Date createTime;



public String getId() {

Return ID;

}



public void setId(String id) {

this.id = id;

}



public String getName() {

Return name;

}



public void setName(String name) {

this.name = name;

}



public String getBirthday() {

return birthday;

}



public void setBirthday(String birthday) {

this.birthday = birthday;

}



public Date getCreateTime() {

return createTime;

}



public void setCreateTime(Date createTime) {

this.createTime = createTime;

}

}


```



+ Create mapper interface to inherit basemapper

```java

@Repository

public interface DemoMapper extends BaseMapper<Demo, String> {

}

```



+ Create test class

```java

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonMapperApplicationTests {

@Autowired

DemoMapper demoMapper;

@Test

public void contextLoads() {

  demoMapper.select();
  
  }
}

```



Annotation of the Chinese characters


#### **@Table** configure entity correspondence table

+Value: database table name



#### **@Column** configure entity properties and corresponding field information

+ Value: the corresponding database field. If it is not configured with the default attribute name, the hump name will be changed to underline name


#### **@Primary** configure the property as primary key

+ Value: property is primary key



#### **@Joincolumn** configure association table

+ Tablename: table to associate

+ Column: which field in the associated table to show
+ relations: field relations

#### **@Association** Field Association

+ Target: field in the corresponding table of the current entity

+ Association: corresponding field in association table

#### **@Transient** configuration property is not in the database
