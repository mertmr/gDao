package gDao.genericDao;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleDaoHandler implements ApplicationContextAware, InitializingBean, BeanPostProcessor {
    private ApplicationContext applicationContext;
    private static Logger logger = Logger.getLogger(SimpleDaoHandler.class);
    private GDao gDao;

    @Override
    public void afterPropertiesSet() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            try {
                Object bean = applicationContext.getBean(beanDefinitionName);
                injectGDao(bean);

            } catch (Exception e) {
                logger.warn("Problem injecting the Simple Dao: " + beanDefinitionName);
            }
        }
    }

    private void injectGDao(Object bean) throws Exception {
        Class<? extends Object> beanClass;
        if (bean instanceof Advised) {
            Advised advisedServiceBean = (Advised) bean;
            bean = advisedServiceBean.getTargetSource().getTarget();
            beanClass = bean.getClass();
        } else
            beanClass = bean.getClass();

        Field[] fields = beanClass.getDeclaredFields();
        List<SimpleAnnotatedField> simpleDaoAnnotatedFieldList = new ArrayList<SimpleAnnotatedField>();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof SimpleDao) {
                    simpleDaoAnnotatedFieldList.add(new SimpleAnnotatedField(field, ((SimpleDao) annotation).value()));
                }
            }

        }

        if (simpleDaoAnnotatedFieldList.size() != 0) {
            for (SimpleAnnotatedField annotatedField : simpleDaoAnnotatedFieldList) {
                Field field = annotatedField.getAnnotatedField();
                field.setAccessible(true);
                GDao copyOfGDao = (GDao) BeanUtils.cloneBean(gDao);
//                ProxyFactory proxyFactory = new ProxyFactory();
//                factory.
//                factory.addAdvisors(advisedGDao.getAdvisors());
                copyOfGDao.setPersistentClass(annotatedField.getPersistentClass());
                field.set(bean, copyOfGDao);
            }

        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
        this.gDao = (GDao) applicationContext.getBean("GDaoImpl");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            injectGDao(bean);
        } catch (Exception e) {
            throw new BeanInstantiationException(bean.getClass(), e.getMessage(), e.getCause());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            injectGDao(bean);
        } catch (Exception e) {
            throw new BeanInstantiationException(bean.getClass(), e.getMessage(), e.getCause());
        }
        return bean;
    }
}