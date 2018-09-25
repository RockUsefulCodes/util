package br.com.rockstom.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It generate a random hexadecimal color or rgb color based on any value param.
 * Arquivo: ColorHashGenerator.java <br/>
 * @since 19 de abr de 2018
 * @author Wesley Luiz
 * @version 1.0.0
 */
public final class ColorHashGenerator {

	private static ColorHashGenerator instance;
	
	private ColorHashGenerator() { }
	
	public static ColorHashGenerator getInstance() {
		if (instance == null) {
			instance = new ColorHashGenerator();
		}
		
		return instance;
	}
	
	/**
	 * Get dynamic hexadecimal color.
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	public String getHexColor(Object value) {
		if (value != null) {
			return rgbToHex(this.getRgbColor(value.toString()));
		}
		
		throw new RuntimeException("Value should not be null");
	}

	/**
	 * Get dynamic RGB color.
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	public List<Long> getRgbColor(Object value) {
		if (value != null) {
			double[] hsl = this.hsl(value.toString());
			return hslToRgb(hsl);
		}
		
		throw new RuntimeException("Value should not be null");
	}
	
	/**
	 * Convert RGB code into Hexadecimal code.
	 * @author Wesley Luiz
	 * @param rgb
	 * @return
	 */
	private String rgbToHex(List<Long> rgb) {
	    return String.format("#%02x%02x%02x", rgb.get(0), rgb.get(1), rgb.get(2));
	}
	
	/**
	 * Convert HSL code into RGB code.
	 * @author Wesley Luiz
	 * @param hsl
	 * @return
	 */
	private List<Long> hslToRgb(double[] hsl) {
		double hue = hsl[0];
		double sat = hsl[1];
		double lig = hsl[2];
		
		hue /= 360;

	    double q = lig < 0.5 ? lig * (1 + sat) : lig + sat - lig * sat;
	    double p = 2 * lig - q;
	    
		return Arrays.asList(hue + 1/3d, hue, hue - 1/3d).stream().map(color -> {
	        if(color < 0) {
	            color++;
	        }
	        
	        if(color > 1) {
	            color--;
	        }
	        
	        if(color < 1/6d) {
	            color = p + (q - p) * 6 * color;
	        } else if(color < 0.5) {
	            color = q;
	        } else if(color < 2/3d) {
	            color = p + (q - p) * 6 * (2/3d - color);
	        } else {
	            color = p;
	        }
	        
	        return Math.round(color * 255);
	    })
	    .collect(Collectors.toList());
	}
	
	/**
	 * Generate HSL (Hue, Saturation and Lightness) code.
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	private double[] hsl(String value) {
		Long hash = this.bkdrHash(value);
		
		long h = (long) hash % 359;
		
		double[] base = new double[] {0.35, 0.5, 0.65};
		
		hash = hash/360;
		
		Long si = hash % base.length;
		
		double s = base[si.intValue()];
		
		hash = hash / base.length;
		
		Long li = hash%base.length;
		
		double l = base[li.intValue()];
		
		return new double[] {h,s,l};
	}

	/**
	 * Generate a BKDR Hash Code
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	private Long bkdrHash(String value) {
		long seed = 131;
		long seed2 = 137;
		long hash = 0;
		
		String v = value + "x";
		
		long maxSafeInteger = (9007199254740991L / seed2);
		
		for (int i = 0; i < v.length(); i++) {
			if (hash > maxSafeInteger) {
				hash = (hash / seed2);
			}
			
			hash = (hash * seed) + v.charAt(i);
		}
		
		return hash;
	}
}
