import {  ProductionOrderProduced } from 'core/types/ProductionOrder';
import ProductionOrderProducedEditModal from '../ProductionOrderProducedEditModal';


import './styles.scss';

type Props = {
    productionOrderProduced: ProductionOrderProduced;
    onEditItem: (productionOrderProduced: ProductionOrderProduced) => void;
    onDeleteItem: (productionOrderProduced: ProductionOrderProduced) => void;
    formulationId:number;
}

function ProductionOrderProducedCard({ productionOrderProduced, onEditItem, onDeleteItem,formulationId }: Props) {

    const deleteItem = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault()
        onDeleteItem(productionOrderProduced)
    }

    return (
        <div className="productionOrder-item-card-main">
            <div className="productionOrder-item-card-inf">
               <div className="productionOrder-item-card-inf-type">
                    <h6 >{productionOrderProduced.palletStatus.name}</h6>
                    <small>{productionOrderProduced.manufacturingDate}</small>
               </div>
                

                <div className="productionOrder-item-card-inf-name">
                    <h6 >{productionOrderProduced.product.name}</h6>
                    <small>{`Pallet: ${productionOrderProduced.pallet} Lote: ${productionOrderProduced.lote}`}</small>
                    
                </div>

                <h6 className="productionOrder-item-card-inf-quantity">{productionOrderProduced.quantity} {productionOrderProduced.product.unity.id} </h6>

                <div className="productionOrder-item-actions">
                    
                    <ProductionOrderProducedEditModal
                        productionOrderProduced={productionOrderProduced}
                        onEditItem={onEditItem}
                        formulationId={formulationId}
                    />

                    <button
                        className="btn btn-danger btn-sm productionOrder-item-btn-del"
                        onClick={(e) => deleteItem(e)}
                    >
                        Deletar
                </button>
                </div>

            </div>
        </div>
    );
}
export default ProductionOrderProducedCard;
