package cn.com.service.admin.impl;

import cn.com.entity.admin.Sn;
import cn.com.service.admin.SnService;
import org.apache.shiro.util.Assert;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Li Sir
 * @Date: 2019/1/14 10:57
 * @Description: 君不见高堂明镜悲白发，朝如青丝暮成雪
 */
@Service
public class SnServiceImpl  implements SnService {

    @Autowired
    private SQLManager sqlManager;

    @Value("${sn.customer.prefix}")
    private String customerPrefix ;
    @Value("${sn.customer.maxLo}")
    private int customerMaxLo ;

    /** 货品编号生成器 */
    private HiloOptimizer customerHiloOptimizer = new HiloOptimizer(Sn.Type.product, customerPrefix, customerMaxLo);

    /**
     * 生成序列号
     *
     * @param type
     *            类型
     * @return 序列号
     */
    public String generate(Sn.Type type) {
        Assert.notNull(type);

        switch (type) {
            case product:
                return customerHiloOptimizer.generate();
            case infoMatch:
                return customerHiloOptimizer.generate();
            case order:
                return customerHiloOptimizer.generate();
        }
        return null;
    }

    /**
     * 获取末值
     *
     * @param type
     *            类型
     * @return 末值
     */

    private Long getLastValue(Sn.Type type) {
        Query<Sn> query = sqlManager.query(Sn.class);
        Sn sn = query.andEq("type", type.ordinal()).select().get(0);
        Long lastValue = sn.getLastValue();
        Query<Sn> updateQuery = sqlManager.query(Sn.class);
        sn.setLastValue(lastValue+1);
        int result = updateQuery.andEq("type", type.ordinal()).andEq("last_value", lastValue).update(sn);
        return 0 < result ? lastValue : getLastValue(type);
    }


    /**
     * 高低位算法生成器
     */
    private class HiloOptimizer {

        /** 类型 */
        private Sn.Type type;

        /** 前缀 */
        private String prefix;

        /** 最大低位值 */
        private int maxLo;

        /** 低位值 */
        private int lo;

        /** 高位值 */
        private long hi;

        /** 末值 */
        private long lastValue;

        /**
         * 构造方法
         *
         * @param type
         *            类型
         * @param prefix
         *            前缀
         * @param maxLo
         *            最大低位值
         */
        public HiloOptimizer(Sn.Type type, String prefix, int maxLo) {
            this.type = type;
            this.prefix = prefix != null ? prefix.replace("{", "${") : "";
            this.maxLo = maxLo;
            this.lo = maxLo + 1;
        }

        /**
         * 生成序列号
         *
         * @return 序列号
         */
        public synchronized String generate() {
            if (lo > maxLo) {
                lastValue = getLastValue(type);
                lo = lastValue == 0 ? 1 : 0;
                hi = lastValue * (maxLo + 1);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            return  dateFormat.format(new Date())+(prefix) + (hi + lo++);
        }
    }


}
