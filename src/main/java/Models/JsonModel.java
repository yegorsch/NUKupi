package Models;

abstract class JsonModel {
   abstract void initializeWith(String JSONString);
   abstract String toJSON();
}
