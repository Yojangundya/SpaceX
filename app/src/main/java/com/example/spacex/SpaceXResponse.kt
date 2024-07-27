package com.example.spacex



data class SpaceXResponse(
    val flight_number: Int,
    val mission_name: String,
    val mission_id: List<String>,
    val upcoming: Boolean,
    val launch_year: String,
    val launch_date_unix: Long,
    val launch_date_utc: String,
    val launch_date_local: String,
    val is_tentative: Boolean,
    val tentative_max_precision: String,
    val tbd: Boolean,
    val launch_window: Int,
    val rocket: Rocket,
    val ships: List<String>,
    val telemetry: Telemetry,
    val launch_site: LaunchSite,
    val launch_success: Boolean?,
    val launch_failure_details: LaunchFailureDetails?,
    val links: Links,
    val details: String?,
    val static_fire_date_utc: String,
    val static_fire_date_unix: Long,
    val timeline: Timeline,
    val crew: List<String>?
)


data class Rocket(
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val first_stage: FirstStage,
    val second_stage: SecondStage,
    val fairings: Fairings?
)


data class FirstStage(
    val cores: List<Core>
)

data class Core(
    val core_serial: String,
    val flight: Int,
    val block: Int?,
    val gridfins: Boolean,
    val legs: Boolean,
    val reused: Boolean,
    val land_success: Boolean?,
    val landing_intent: Boolean,
    val landing_type: String?,
    val landing_vehicle: String?
)

data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
)

data class Payload(
    val payload_id: String,
    val norad_id: List<Int>,
    val reused: Boolean,
    val customers: List<String>,
    val nationality: String,
    val manufacturer: String,
    val payload_type: String,
    val payload_mass_kg: Int,
    val payload_mass_lbs: Int,
    val orbit: String,
    val orbit_params: OrbitParams
)


data class OrbitParams(
    val reference_system: String,
    val regime: String,
    val longitude: Double?,
    val semi_major_axis_km: Double?,
    val eccentricity: Double?,
    val periapsis_km: Int,
    val apoapsis_km: Int,
    val inclination_deg: Double,
    val period_min: Double?,
    val lifespan_years: Double?,
    val epoch: String?,
    val mean_motion: Double?,
    val raan: Double?,
    val arg_of_pericenter: Double?,
    val mean_anomaly: Double?
)

data class Fairings(
    val reused: Boolean,
    val recovery_attempt: Boolean,
    val recovered: Boolean,
    val ship: String?
)

data class Telemetry(
    val flight_club: String?
)

data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
)

data class LaunchFailureDetails(
    val time: Int,
    val altitude: Double?,
    val reason: String
)

data class Links(
    val mission_patch: String?,
    val mission_patch_small: String?,
    val reddit_campaign: String?,
    val reddit_launch: String?,
    val reddit_recovery: String?,
    val reddit_media: String?,
    val presskit: String?,
    val article_link: String?,
    val wikipedia: String?,
    val video_link: String?,
    val youtube_id: String?,
    val flickr_images: List<String>
)

data class Timeline(
    val webcast_liftoff: Int
)
