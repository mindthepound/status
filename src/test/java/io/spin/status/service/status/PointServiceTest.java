//package io.spin.cms.service.point;
//
//import io.spin.cms.dto.DynamoDBSearchParam;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles(profiles = "jenkins")
//public class PointServiceTest {
//
//    @Autowired
//    private PointService pointService;
//
//    @Test
//    public void getPointByOwnerTest() {
//        DynamoDBSearchParam dynamoDBSearchParam = new DynamoDBSearchParam();
//        dynamoDBSearchParam.setOwner("592b61b47937d7115085ba4f");
//
//        Assert.assertNotNull(pointService.getPointByOwner(dynamoDBSearchParam));
//    }
//
//}
