package org.tinyspring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author tangyingqi
 * @date 2018/6/27
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        ResourceTest.class})
public class V1AllTest {
}
