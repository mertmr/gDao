/*
 * Copyright 2013 Mert Meral
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gDao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.Conventions;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author Mert Meral
 * @since 0.1.1
 */
public class SimpleDaoTestInjectHandler extends AbstractTestExecutionListener {

    public static final String REINJECT_DEPENDENCIES_ATTRIBUTE = Conventions.getQualifiedAttributeName(
            SimpleDaoTestInjectHandler.class, "reinjectDependencies");

    private static final Log logger = LogFactory.getLog(SimpleDaoTestInjectHandler.class);

    @Override
    public void prepareTestInstance(final TestContext testContext) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Performing dependency injection for test context [" + testContext + "].");
        }
        injectDependencies(testContext);
    }

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        if (Boolean.TRUE.equals(testContext.getAttribute(REINJECT_DEPENDENCIES_ATTRIBUTE))) {
            if (logger.isDebugEnabled()) {
                logger.debug("Reinjecting dependencies for test context [" + testContext + "].");
            }
            injectDependencies(testContext);
        }
    }

    protected void injectDependencies(final TestContext testContext) throws Exception {
        Object bean = testContext.getTestInstance();
        AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();
        beanFactory.autowireBeanProperties(bean, AutowireCapableBeanFactory.AUTOWIRE_NO, false);
        beanFactory.initializeBean(bean, testContext.getTestClass().getName());
        testContext.removeAttribute(REINJECT_DEPENDENCIES_ATTRIBUTE);
    }

}
