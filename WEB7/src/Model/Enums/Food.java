package Model.Enums;

/**
 * Represents available food.
 */
public enum Food {
    VEGETARIAN{
        public String toString() {
            return "vegetarian";
        }
    },
    VEGAN{
        public String toString() {
            return "vegan";
        }
    },
    ALL{
        public String toString() {
            return "all";
        }
    }
}
