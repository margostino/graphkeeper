package org.margostino.schema;

import graphql.language.ListType;
import graphql.language.Type;
import graphql.language.TypeName;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLScalarType;

import static graphql.Scalars.*;
import static org.margostino.type.CustomScalar.GraphQLInvalid;
import static org.margostino.type.CustomScalar.GraphQLLong;

public class TypeBuilder {

    protected static GraphQLList getListType(Type type) {
        if (type instanceof ListType) {
            GraphQLScalarType scalarType = getScalarType((((ListType) type).getType()));
            return GraphQLList.list(scalarType);
        }
        return GraphQLList.list(GraphQLInvalid);
    }

    protected static GraphQLScalarType getScalarType(Type type) {
        final String typeName = ((TypeName) type).getName();

        switch (typeName) {
            case "String":
                return GraphQLString;
            case "Long":
                return GraphQLLong;
            case "Int":
                return GraphQLInt;
            case "Boolean":
                return GraphQLBoolean;
            case "Float":
                return GraphQLFloat;
            case "ID":
                return GraphQLID;
            default:
                return GraphQLInvalid;
        }
    }
}
