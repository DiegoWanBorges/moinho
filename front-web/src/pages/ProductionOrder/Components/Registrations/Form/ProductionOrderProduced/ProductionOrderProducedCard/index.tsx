import { ProductionOrderProduced } from 'core/types/ProductionOrder';
import ProductionOrderProducedEditModal from '../ProductionOrderProducedEditModal';
import Print from 'core/assets/images/print.png'
import { makePrivateRequest } from 'core/utils/request';
import { toast } from 'react-toastify';
import './styles.scss';

type Props = {
    productionOrderProduced: ProductionOrderProduced;
    onEditItem: (productionOrderProduced: ProductionOrderProduced) => void;
    onDeleteItem: (productionOrderProduced: ProductionOrderProduced) => void;
    formulationId: number;
}

function ProductionOrderProducedCard({ productionOrderProduced, onEditItem, onDeleteItem, formulationId }: Props) {
    const onPrint = () => {
        makePrivateRequest({ url: `/productionordersproduced/pdf?productionOrderId=${productionOrderProduced.productionOrderId}&pallet=${productionOrderProduced.pallet}`, responseType: "blob" })
            .then(response => {
                //Build a URL from the file
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                //Open the URL on new Window
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
    }
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

                    <img
                        className="ProductionOrderItemCard-btn-print"
                        src={Print} alt=""
                        onClick={onPrint}
                    />

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
