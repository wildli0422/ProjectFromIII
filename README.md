# ProjectFromIII

請在H2 console 中建立

jdbc:h2:tcp://localhost/mem:testdb 
username:sa
password:sa 

再於server context.xml中新增
<Resource auth="Container" driverClassName="org.h2.Driver" maxActive="20" maxIdle="10" maxWait="1" name="jdbc/ZA105G3" password="sa" type="javax.sql.DataSource" url="jdbc:h2:tcp://localhost/mem:testdb" username="sa"/>

再執行
/OneKeyRecoveryDB-Final8/CreateUser 

即可還原資料庫
