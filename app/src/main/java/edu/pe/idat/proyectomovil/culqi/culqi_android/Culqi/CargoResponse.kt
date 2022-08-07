package edu.pe.idat.proyectomovil.culqi.culqi_android.Culqi

data class CargoResponse(
    val amount: Int,
    val amount_refunded: Int,
    val authorization_code: Any,
    val capture: Boolean,
    val capture_date: Long,
    val creation_date: Long,
    val currency_code: String,
    val current_amount: Int,
    val description: Any,
    val dispute: Boolean,
    val duplicated: Boolean,
    val email: String,
    val fee_details: FeeDetails,
    val fraud_score: Any,
    val id: String,
    val installments: Int,
    val installments_amount: Any,
    val metadata: Metadata,
    val `object`: String,
    val outcome: Outcome,
    val paid: Boolean,
    val reference_code: String,
    val source: Source,
    val statement_descriptor: String,
    val total_fee: Int,
    val total_fee_taxes: Int,
    val transfer_amount: Int,
    val transfer_id: Any
)
data class VariableFee(
    val commision: Double,
    val currency_code: String,
    val total: Int
)


data class Source(
    val active: Boolean,
    val card_number: String,
    val client: Client,
    val creation_date: Long,
    val email: String,
    val id: String,
    val iin: Iin,
    val last_four: String,
    val metadata: MetadataX,
    val `object`: String,
    val type: String
)

data class Outcome(
    val code: String,
    val merchant_message: String,
    val type: String,
    val user_message: String
)

data class MetadataX(
    val dni: String
)

class Metadata

data class Issuer(
    val country: String,
    val country_code: String,
    val name: String,
    val phone_number: String,
    val website: String
)

data class Iin(
    val bin: String,
    val card_brand: String,
    val card_category: Any,
    val card_type: String,
    val installments_allowed: List<Any>,
    val issuer: Issuer,
    val `object`: String
)
class FixedFee


data class FeeDetails(
    val fixed_fee: FixedFee,
    val variable_fee: VariableFee
)


data class Client(
    val browser: String,
    val device_fingerprint: Any,
    val device_type: String,
    val ip: String,
    val ip_country: String,
    val ip_country_code: String
)
