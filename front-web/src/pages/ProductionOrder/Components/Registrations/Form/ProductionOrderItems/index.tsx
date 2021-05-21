import { ProductionOrder, ProductionOrderItem } from 'core/types/ProductionOrder';
import ProductionOrderItemsInsert from '../ProductionOrderItemsInsert';
import './styles.scss';

type Props = {
    productionOrder: ProductionOrder;
}

const ProductionOrderItems = ({ productionOrder }: Props) => {
  
    const onInsertItem = (data: ProductionOrderItem) => {
        console.log(data)
    }
    const onDeleteItem = (data: ProductionOrderItem) => {
        const confirm = window.confirm("Deseja excluir o ingrediente selecionado da formulação?");
        console.log(data)
    }
    const onEditItem = (data: ProductionOrderItem) => {
        console.log(data)
    }
    return (
        <form>
            {<ProductionOrderItemsInsert 
                onInsertItem={onInsertItem}
                productionOrder={productionOrder}
            />
            
            

            }
        </form>
    )
}

export default ProductionOrderItems;