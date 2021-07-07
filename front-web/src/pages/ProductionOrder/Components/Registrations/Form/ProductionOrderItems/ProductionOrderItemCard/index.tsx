import './styles.scss';
import { ProductionOrderItem } from 'core/types/ProductionOrder';
import ProductionOrderItemEditModal from '../ProductionOrderItemsEditModal';
import Print from 'core/assets/images/print.png'
import { makePrivateRequest } from 'core/utils/request';
import { toast } from 'react-toastify';
import Loader from "react-loader-spinner";
import { useState } from 'react';



type Props = {
    productionOrderItem: ProductionOrderItem;
    onEditItem: (productionOrderItem: ProductionOrderItem) => void;
    onDeleteItem: (productionOrderItem: ProductionOrderItem) => void;
}

function ProductionOrderItemCard({ productionOrderItem, onEditItem, onDeleteItem }: Props) {
    const [isLoading, setIsLoading] = useState(false);
    const deleteItem = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault()
        onDeleteItem(productionOrderItem)
    }
    const onPrint = () => {
        setIsLoading(true)
        makePrivateRequest({ url: `/productionorders/reports?id=${productionOrderItem.productionOrderId}&serie=${productionOrderItem.serie}`, responseType: "blob" })
            .then(response => {
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
            .finally(()=>setIsLoading(false))
    }
    return (
        <div className="productionOrderItemCard-main">
            <div className="productionOrderItemCard-inf">
                <h6 className="productionOrderItemCard-inf-type">{productionOrderItem.type}</h6>

                <div className="productionOrderItemCard-inf-name">
                    <h6 >{productionOrderItem.product.name}</h6>
                    <small>Serie: {productionOrderItem.serie} - Ocorrencia: {productionOrderItem.occurrence.name}</small>

                </div>

                <h6 className="productionOrderItemCard-inf-quantity">{productionOrderItem.quantity} {productionOrderItem.product.unity.id} </h6>

                <div className="productionOrderItemCard-actions">
                    {isLoading ? <Loader type="Rings" width={38} height={38} color="#0670B8" /> :(
                        productionOrderItem.serie !== 1 ? (
                            <img
                                className="ProductionOrderItemCard-btn-print"
                                src={Print} alt=""
                                onClick={onPrint}
                            />
                        ) : null
                    )}


                    <ProductionOrderItemEditModal
                        productionOrderItem={productionOrderItem}
                        onEditItem={onEditItem}

                    />
                    <button
                        className="btn btn-danger btn-sm"
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
