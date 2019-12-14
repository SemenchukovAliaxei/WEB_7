package Model.Enums;

/**
 * represents available transport.
 */
public enum Transport {
    BUS{
        public String toString() {
            return "bus";
        }
    },
    PLANE{
        public String toString() {
            return "plane";
        }
    },
    RAILWAY{
        public String toString() {
            return "railway";
        }
    },
    CRUISER{
        public String toString() {
            return "cruiser";
        }
    }
}

