export  const toISOFormatDateTime = (dateTime :string) => {
    // Primeiro, dividimos a data completa em duas partes:
    const [date, time] = dateTime.split(' ');
  
    // Dividimos a data em dia, mês e ano:
    const [DD, MM, YYYY] = date.split('/');
  
    // Dividimos o tempo em hora e minutos:
    const [HH, mm] = time.split(':');
  
    // Retornamos a data formatada em um padrão compatível com ISO:
    return `${YYYY}-${MM}-${DD}T${HH}:${mm}`;
  }

  export  const toISOFormatDate = (date :string) => {
    // Dividimos a data em dia, mês e ano:
    const [DD, MM, YYYY] = date.split('/');
    // Retornamos a data formatada em um padrão compatível com ISO:
    return `${YYYY}-${MM}-${DD}`;
  }

  export const formatPrice =(price:number) =>{
    return new Intl.NumberFormat('pt-BR', {minimumFractionDigits:2}).format(price);
}