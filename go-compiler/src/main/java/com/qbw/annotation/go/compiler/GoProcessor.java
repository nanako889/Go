package com.qbw.annotation.go.compiler;

import com.google.auto.service.AutoService;
import com.qbw.annotation.go.Constant;
import com.qbw.annotation.go.IntentValue;
import com.qbw.annotation.go.compiler.common.Log;
import com.qbw.annotation.go.compiler.common.VariableEnity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

@SupportedAnnotationTypes("com.qbw.annotation.go.IntentValue")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class GoProcessor extends AbstractProcessor {

    private Elements mElements;
    private Filer mFiler;

    //private Set<String> mNotSupportedVariableType;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Log.setMessager(processingEnvironment.getMessager());
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        //mNotSupportedVariableType = getNotSupportedVariableTypes();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<String, ParasitePoet> poetMap = new LinkedHashMap<>();

        for (Element element : roundEnv.getElementsAnnotatedWith(IntentValue.class)) {

            String variableName = element.toString();
            TypeMirror variableType = element.asType();

            //            if (!isVariableTypeSupported(variableType.toString())) {
            //                Log.e("Not support " + variableType.toString());
            //                return false;
            //            }

            if (ElementKind.FIELD != element.getKind()) {
                Log.e(variableName + " should be a field variable");
                return false;
            }

            for (Modifier modifier : element.getModifiers()) {
                if (modifier == Modifier.PRIVATE) {
                    Log.e(variableName + " can not be Private");
                    return false;
                }
            }

            TypeElement parentElement = (TypeElement) element.getEnclosingElement();
            String className = parentElement.getQualifiedName().toString();
            String packageName = mElements.getPackageOf(parentElement).getQualifiedName().toString();
            String simpleClassName = parentElement.getSimpleName().toString();
            String complexClassName = className.substring(className.indexOf(packageName) + packageName.length() + 1);//Inner Class

            //Log.i(String.format("packageName=%s, className=%s, simpleClassName=%s, complexClassName=%s", packageName, className, simpleClassName, complexClassName));

            ParasitePoet variables = poetMap.get(className);
            if (null == variables) {
                variables = new ParasitePoet(mFiler, packageName, Constant.appendSuffix(complexClassName), Constant.appendSuffix(simpleClassName), complexClassName);
                poetMap.put(className, variables);
            }
            variables.getVariableNames().add(new VariableEnity(variableName, variableType));
        }

        if (!poetMap.isEmpty()) {

            ParasitePoet[] poets;
            poetMap.values().toArray(poets = new ParasitePoet[poetMap.values().size()]);
            int ppCount = poets.length;
            for (int i = 0; i < ppCount - 1; i++) {
                for (int j = i + 1; j < ppCount; j++) {
                    String iname = poets[i].getComplexClassName();
                    String jname = poets[j].getComplexClassName();
                    if (iname.equals(jname)) {
                        Log.e(String.format("[Go]\n*****\nIntentValue is not allowed to be used in classes whichs classname is equal, such as [%s.%s, %s.%s]\n*****", poets[i].getPackageName(), iname, poets[j].getPackageName(), jname));
                        return false;
                    }
                }
            }

            new GoPoet(mFiler, poetMap).generate();

            for (Map.Entry<String, ParasitePoet> parasite : poetMap.entrySet()) {
                parasite.getValue().generate();
            }
        }

        return true;
    }

    //    protected Set<String> getNotSupportedVariableTypes() {
    //        Set<String> vts = new HashSet<>();
    //
    //        vts.add(Map.class.getCanonicalName());
    //        vts.add(HashMap.class.getCanonicalName());
    //        vts.add(Hashtable.class.getCanonicalName());
    //        vts.add(WeakHashMap.class.getCanonicalName());
    //        vts.add(LinkedHashMap.class.getCanonicalName());
    //        vts.add(TreeMap.class.getCanonicalName());
    //        vts.add(IdentityHashMap.class.getCanonicalName());
    //
    //        vts.add(Set.class.getCanonicalName());
    //        vts.add(HashSet.class.getCanonicalName());
    //        vts.add(LinkedHashSet.class.getCanonicalName());
    //        vts.add(TreeSet.class.getCanonicalName());
    //
    //        vts.add(List.class.getCanonicalName());
    //        vts.add(Vector.class.getCanonicalName());
    //        vts.add(ArrayList.class.getCanonicalName());
    //        vts.add(LinkedList.class.getCanonicalName());
    //
    //        //vts.add(String[].class.getCanonicalName());
    //
    //        return vts;
    //    }
    //
    //    private boolean isVariableTypeSupported(String canonicalName) {
    //        boolean ret = true;
    //        for (String s : mNotSupportedVariableType) {
    //            if (canonicalName.startsWith(s)) {
    //                ret = false;
    //                break;
    //            }
    //        }
    //        return ret;
    //    }
}
