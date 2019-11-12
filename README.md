使用技术:Spring、SpringMVC、Mybatis框架、Redis、Solr、Mysql、Jsonp跨域数据请求、Dubbo、Nginx、MyBatis 逆向 工程、HttpClient 使用java完成请求及响应、MyCat mysql 分库分表 

开发工具和环境:Eclipsemars、Maven3.3.3、Tomcat7.0.79（MavenTomcatPlugin）、JDK1.7 、Mysql5.7、Nginx1.8.0、Redis3.0.0、Win10 操作系统、Linux(服务器系统)

ego-parent中的pom.xml文件中添加了此project的所有依赖和版本信息
ego-portal前台实现主界面显示、商品搜索功能、商品展示功能、显示购物车功能、提交订单功能、用户注册登录功能 
ego-manage后台实现商品管理 
ego-pojo中为数据库中表对应的实体类
ego-redis中为redis中的增删改查操作
ego-commons为工具类
ego-item为商品操作项目
ego-search为根据关键字查询商品
ego-service为RPC服务中定义的接口
ego-service-impl为RPC服务中实现接口方法的主类

1.数据库中的表通过MyBatis逆向工程直接生成pojo类和mapper.xml文件以及接口 
2.通过使用dubbo是实现了RPC服务，以zookeeper注册中心，便于多个项目操作相同表时代码冗余，不同的项目直接调用服务中的方法，程序员无法看到方法如何实现，只知道接口方法的作用 
3.后台商品图片通过vsftpd全部上传到nginx，后台和前台显示图片都是通过nginx反向代理获取真实地址 
4.ego-portal项目中的显示商品类目功能，第一次通过从关系型数据库中查询出结果，并将其存放到redis中   后台通过关系型数据库查询数据直接存放到redis缓存中，前台页面直接先从redis中获取数据，如果不存在，才从关系型数据库查询并且将获取的数据也同步到redis中，提高前台页面的性能 
5.前台通过使用solr框架实现商品搜索功能 
6.不同项目中的数据请求通过使用HttpClient完成请求及响应 前台不同页面之间的跨域跳转通过使用jsonp实现跨域数据请求
7.通过使用cookie+redis实现单点登录 将cookie中的uuid作为redis中的key 数据库中获取的数据作为value存入redis并设置有效期和cookie一致  cookie中将jsessionid作为key  uuid作为value 每次访问时请求中都会判断是否存在此cookie
8.9.使用mycat进行分库利用mySQL 主从备份功能实现读写分离.提高性能 防止数据库压力过大 使用 Nginx 实现负载均衡 使用多个服务器且每个服务器中都部署相同的项目 使用nginx 反向代理多个服务器通过调节属性 weight的值实现服务器的负载均衡
