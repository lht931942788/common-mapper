package cn.org.rookie.mapper.annotation;

/**
 * 配置两表之间的关系
 */
public @interface Association {

    /**
     * 配置当前表字段名称
     *
     * @return 当前表字段名称
     */

    String target();

    /**
     * 配置关联表字段名称
     *
     * @return 关联表字段名称
     */

    String association();

}
