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
        <div className="producedCard-main">
            <div className="producedCard-inf">
               <div className="producedCard-inf-type">
                    <h6 >{productionOrderProduced.palletStatus.name}</h6>
                    <small>{productionOrderProduced.manufacturingDate}</small>
               </div>
                

                <div className="producedCard-inf-name">
                    <h6 >{productionOrderProduced.product.name}</h6>
                    <small>{`Pallet: ${productionOrderProduced.pallet} Lote: ${productionOrderProduced.lote}`}</small>
                    
                </div>

                <h6 className="producedCard-inf-quantity">{productionOrderProduced.quantity} {productionOrderProduced.product.unity.id} </h6>

                <div className="producedCard-actions">
                    
                    <ProductionOrderProducedEditModal
                        productionOrderProduced={productionOrderProduced}
                        onEditItem={onEditItem}
                        formulationId={formulationId}
                    />

                    <button
                        className="btn btn-danger btn-sm producedCard-btn-del"
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
