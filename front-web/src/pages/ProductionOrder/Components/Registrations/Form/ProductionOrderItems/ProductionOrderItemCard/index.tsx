import { ProductionOrderItem } from 'core/types/ProductionOrder';
import ProductionOrderItemEditModal from '../ProductionOrderItemsEditModal';

import './styles.scss';

type Props = {
    productionOrderItem: ProductionOrderItem;
    onEditItem: (productionOrderItem: ProductionOrderItem) => void;
    onDeleteItem: (productionOrderItem: ProductionOrderItem) => void;
}

function ProductionOrderItemCard({ productionOrderItem, onEditItem, onDeleteItem }: Props) {

    const deleteItem = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault()
        onDeleteItem(productionOrderItem)
    }

    return (
        <div className="productionOrder-item-card-main">
            <div className="productionOrder-item-card-inf">
                <h6 className="productionOrder-item-card-inf-type">{productionOrderItem.type}</h6>

                <div className="productionOrder-item-card-inf-name">
                    <h6 >{productionOrderItem.product.name}</h6>
                    <small>Serie: {productionOrderItem.serie} - Ocorrencia: {productionOrderItem.occurrence.name}</small>
                    
                </div>

                <h6 className="productionOrder-item-card-inf-quantity">{productionOrderItem.quantity} {productionOrderItem.product.unity.id} </h6>

                <div className="productionOrder-item-actions">
                    <ProductionOrderItemEditModal
                        productionOrderItem={productionOrderItem}
                        onEditItem={onEditItem}
                        
                    />
                    <button
                        className="btn btn-danger btn-sm "
                        onClick={(e) => deleteItem(e)}
                    >
                        Deletar
                </button>
                </div>

            </div>
        </div>
    );
}
export default ProductionOrderItemCard;
