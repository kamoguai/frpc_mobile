import com.frpclib.FrpclibGetVersion

object IosFrpc {
    fun version(): String {
        return FrpclibGetVersion()
    }
}