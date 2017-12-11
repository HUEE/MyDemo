package hwj.com.ioc_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hwj on 17-10-11.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface IBindView {
    int value ();
}
