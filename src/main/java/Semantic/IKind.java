package Semantic;

import Semantic.Enum.Kind;
import Semantic.Enum.ReturnType;
import com.sun.corba.se.impl.ior.EncapsulationUtility;

public interface IKind {

    Kind getKind();
    ReturnType getReturnType();
}
