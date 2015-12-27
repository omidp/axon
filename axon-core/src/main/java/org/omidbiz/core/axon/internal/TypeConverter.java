package org.omidbiz.core.axon.internal;


/**
 * T : is the Object which is currently being serialized
 * <pre> 
 *  {@code
 *  class FieldConvertor implements TypeConverter<Model>{
 *  
 *  }
 *  </pre>
 *  <p>
 *  whole object will be replaced with the write element if condition applied.
 *  </p>
 * @author : Omid Pourhadi omidpourhadi [AT] gmail [DOT] com
 * @author Saber Golanbari Golanbari [AT] gmail [DOT] com
 * @version 0.2
 */
public interface TypeConverter<T>
{
    /**
     * a condition where you can check which object should be serialized and replaced with 
     * @param instance
     * @return
     */
    boolean applies(Object instance);
    
    /**
     * @see BasicElement
     * @param instance
     * @param ctx
     * @return
     */
    Element write(T instance, SerializationContext ctx);

}
