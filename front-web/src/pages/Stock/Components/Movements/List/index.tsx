import { useCallback, useEffect, useState } from 'react';
import './styles.scss'
import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';
import Pagination from 'core/components/Pagination';
import Select from 'react-select';
import { Product } from 'core/types/Product';
import { makePrivateRequest } from 'core/utils/request';
import { StockMovementsResponse } from 'core/types/StockMovement';
import StockMovementCard from '../Card';
import history from 'core/utils/history';
import { toast } from 'react-toastify';
import StockMovementBalance from './StockMovementBalance'
import CardLoader from 'core/components/CardLoader';

const StockMovementList = () => {
    const [startDate, setStartDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1));
    const [endDate, setEndDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 1, 0));
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);
    const [product, setProduct] = useState<Product | null>();
    const [stockMovementResponse, setStockMovementResponse] = useState<StockMovementsResponse>();
    const [activePage, setActivePage] = useState(0);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoadingProducts(true);
        makePrivateRequest({ url: `/products/list?name=` })
            .then(response => {
                setProducts(response.data)
            })
            .finally(() => setIsLoadingProducts(false))
    }, [])

    const getStockMovements = useCallback(() => {
        setIsLoading(true)
        const params = {
            page: activePage,
            startDate: moment(startDate).format("DD/MM/YYYY"),
            endDate: moment(endDate).format("DD/MM/YYYY"),
            linesPerPage: 10,
            orderBy: "date",
            direction: "DESC",
            productId: product?.id
        }
        makePrivateRequest({ url: '/stocks', params })
            .then(response => setStockMovementResponse(response.data))
            .finally(() => setIsLoading(false))
    }, [activePage, startDate, endDate, product])

    useEffect(() => {
        getStockMovements()
        // eslint-disable-next-line
    }, [activePage, getStockMovements, product])

    const handCreate = () => {
        history.push("/stock/movements/new");
    }

    const onRemove = (stockMovementId: number) => {
        const confirm = window.confirm("Deseja excluir o movimento selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/stocks/${stockMovementId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Movimentação de estoque excluida com sucesso!")
                    history.push('/stock/movements/')
                    getStockMovements();
                })
                .catch((error) => {
                    toast.error(error.response.data.message)
                    history.push('/stock/movements/')
                })
        }
    }
    return (
        <div className="stockMovementList-main">
            <div className="stockMovementList-actions">
                <button
                    className="btn btn-primary btn-lg stockMovementList-btnAdd"
                    onClick={handCreate}
                >
                    ADCIONAR
                </button>
            </div>

            <div className="stockMovementList-filter">

                <div className="stockMovementList-filter-startDate">
                    <label className="label-base">Dt. Inicial:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={startDate}
                    />
                </div>

                <div className="stockMovementList-filter-endDate">
                    <label className="label-base">Dt. Final:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat={false}
                        onChange={(e) => setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                    />
                </div>

                <div className="stockMovementList-filter-product">
                    <label className="label-base">Produto:</label>
                    <Select
                        options={products}
                        isLoading={isLoadingProducts}
                        getOptionLabel={(option: Product) => option.name}
                        getOptionValue={(option: Product) => String(option.id)}
                        placeholder="Todos"
                        onChange={(e) => setProduct(e)}
                        isClearable
                    />
                </div>
            </div>

            {product && (
                <div className="stockMovementList-balance">
                    <StockMovementBalance
                        product={product}
                        startDate={moment(startDate).format("DD/MM/YYYY")}
                        endDate={moment(endDate).format("DD/MM/YYYY")}
                    />
                </div>
            )}

            {isLoading ? <CardLoader /> : (
                stockMovementResponse?.content.map(item => (
                    <StockMovementCard
                        stockMovement={item}
                        onRemove={onRemove}
                        key={item.id}
                    />
                )))}

            {stockMovementResponse &&
                <Pagination
                    totalPages={stockMovementResponse?.totalPages}
                    onChange={page => setActivePage(page)}
                />
            }
        </div>
    )
}

export default StockMovementList