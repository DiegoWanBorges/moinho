import './styles.scss';

type Props = {
    productionOrderId: string;
}

const ProductionOrderProduced = ({ productionOrderId }: Props) => {
    return (
        <div>
            <h1>{productionOrderId}</h1>
        </div>
    )
}

export default ProductionOrderProduced