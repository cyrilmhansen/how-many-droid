package fr.softwaresemantics.howmanydroid.model.formula;

/**
 * Created by cmh on 13/12/13.
 */
public enum FormulaSyntax {
    TEX, DISPLAY_MATHML, ASCII_MATHML;

    public String getMJStartSeparator() {
        switch (this) {
            case TEX:
                return "";
            default:
            case DISPLAY_MATHML:
                return "";
            case ASCII_MATHML:
                return "";
        }
    }

    public String getMJEndSeparator() {
        switch (this) {
            case TEX:
                return "";
            default:
            case DISPLAY_MATHML:
                return "";
            case ASCII_MATHML:
                return "";
        }
    }


    public Object getId() {
        switch (this) {
            case TEX:
                return "tex_math";
            case DISPLAY_MATHML:
                return "mm_math";
            default: // Exception ??
            case ASCII_MATHML:
                return "am_math";
        }
    }
}
