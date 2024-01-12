package br.com.aldemir.common.util

inline fun <T1, T2, R> safeLet(
    p1: T1?,
    p2: T2?,
    block: (T1, T2) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            block(safeP1, safeP2)
        }
    }
}

inline fun <T1, T2, T3, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                block(safeP1, safeP2, safeP3)
            }
        }
    }
}

inline fun <T1, T2, T3, T4, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                p4?.let { safeP4 ->
                    block(safeP1, safeP2, safeP3, safeP4)
                }
            }
        }
    }
}