import './styles.scss';
import {  ProductionOrderProduced } from 'core/types/ProductionOrder';
type Props = {
    productionOrderProduced: ProductionOrderProduced;
}

const ProductionOrderDetailsProduced = ({ productionOrderProduced }: Props) => {
    return (
        <div className="productionOrderDetailsProduced-main">
            <div className="productionOrderDetailsProduced-product">
                {productionOrderProduced.product.name}
            </div>
            <div className="productionOrderDetailsProduced-product">
                {`Pallet: ${productionOrderProduced.pallet} Status: ${productionOrderProduced.palletStatus.name}`}
            </div>
            <div className="productionOrderDetailsProduced-quantity">
                {`${productionOrderProduced.quantity} ${productionOrderProduced.product.unity.id}`}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsProduced;