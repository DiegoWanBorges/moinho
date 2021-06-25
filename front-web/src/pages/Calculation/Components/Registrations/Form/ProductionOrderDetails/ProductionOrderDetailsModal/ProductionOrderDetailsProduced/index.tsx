import './styles.scss';
import {  ProductionOrderProduced } from 'core/types/ProductionOrder';
type Props = {
    productionOrderProduced: ProductionOrderProduced;
}

const ProductionOrderDetailsProduced = ({ productionOrderProduced }: Props) => {
    return (
        <div className="productionOrderDetailsInputs-main">
            <div>
                {productionOrderProduced.product.name}
            </div>
        </div>
    )
}

export default ProductionOrderDetailsProduced;