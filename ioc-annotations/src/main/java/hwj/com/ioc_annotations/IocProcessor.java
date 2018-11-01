package hwj.com.ioc_annotations;

import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/** Created by hwj on 17-10-11. */
@AutoService(Processor.class)
public class IocProcessor extends AbstractProcessor {
  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
    return false;
  }

  @Override
  public Set<String> getSupportedOptions() {
    return super.getSupportedOptions();
  }

  /**
   * 支持的注解类型
   *
   * @return
   */
  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> annotationTypes = new LinkedHashSet<String>();
    annotationTypes.add(IBindView.class.getCanonicalName());
    return annotationTypes;
  }

  /**
   * 返回支持的源码版本
   *
   * @return
   */
  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
  }
}
