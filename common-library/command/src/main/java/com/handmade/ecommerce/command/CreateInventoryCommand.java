package com.handmade.ecommerce.command;

public class CreateInventoryCommand {
        private String id;
        private String variantId;
        private int quantityAvailable;
        private int quantityReserved;
        private boolean isBackOrderAllowed;

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getVariantId() {
                return variantId;
        }

        public void setVariantId(String variantId) {
                this.variantId = variantId;
        }

        public int getQuantityAvailable() {
                return quantityAvailable;
        }

        public void setQuantityAvailable(int quantityAvailable) {
                this.quantityAvailable = quantityAvailable;
        }

        public int getQuantityReserved() {
                return quantityReserved;
        }

        public void setQuantityReserved(int quantityReserved) {
                this.quantityReserved = quantityReserved;
        }

        public boolean isBackOrderAllowed() {
                return isBackOrderAllowed;
        }

        public void setBackOrderAllowed(boolean backOrderAllowed) {
                isBackOrderAllowed = backOrderAllowed;
        }
}
